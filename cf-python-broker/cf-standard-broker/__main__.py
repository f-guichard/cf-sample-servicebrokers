# -*- coding: UTF-8 -*-

from flask import Flask
from flask import jsonify
from flask import request
import os

if os.getenv("VCAP_APP_PORT") > 1024:
   cfPort = int(os.getenv("VCAP_APP_PORT"))
else:
   cfPort = 8080

app = Flask(__name__)

myUnsupportedOperation = { "description": "operation not supported at this time" }

#See http://docs.cloudfoundry.org/services/api.html#catalog-mgmt
myDumbCatalog = { 
  "services": [{
    "id": "3F2504E0-4F89-11D3-9A0C-0305E82C9999",
    "name": "o-varnish",
    "description": "varnish backend as a service" ,
    "bindable": False,
    "plans": [{
      "id": "3F2504E0-4F89-11D3-9A0C-0305E82C9998",
      "name": "low",
      "description": "A low powered varnish with limits to 100 hit/s"
    },{
      "id": "3F2504E0-4F89-11D3-9A0C-0305E82C9997",
      "name": "large",
      "description": "A high powered varnish with limits to 1000 hit/s",
      "free": False
    }],
    "dashboard_client": {
      "id": "mon-client-1",
      "secret": "secret-1",
      "redirect_uri": "https://varnish.cfy.dfy.ftinet"
    }
  }]
}

helper = {
  "/v2/catalog": {
     "method": "GET",
     "parameters": "",
     "description": "REST Call pour obtenir le catalogue de service offert par ce broker"
  },
  "/v2/service_instances/<int:instance_id>": {
     "method": "PUT",
     "parameters": "instance_id",
     "description": "REST Call pour provisionner le service offert par ce broker"
  },
  "/v2/service_instances/<int:instance_id>/last_operation": {
     "method": "GET",
     "parameters": "instance_id",
     "description": "REST Call pour obtenir l'etat de la derniere operation realisee par ce broker (in progress or succeeded or failed)"
  }
}

@app.route('/')
def index():
    return 'cf-standard-broker : / not supported => see /help'

@app.route('/help')
def help():
	return jsonify(helper)

#Cloudfoundry needed routes
@app.route('/v2/service_instances/<int:instance_id>/last_operation', methods=['GET'])
def last_operation(instance_id):
    response = jsonify(myUnsupportedOperation)
    response.status_code = 257
    return response

@app.route('/v2/catalog')
def get_catalog():
    response = jsonify(myDumbCatalog)
    response.status_code = 200
    return response

@app.route('/v2/service_instances/<instance_id>', methods=['PUT'])
def bind_service(instance_id):
    paramincomplete = request.args.get('accepts_incomplete', '')
    response = jsonify({'description':'recu bind service_instance %s, avec le param accepts_incomplete %s => pas implemente' % (instance_id, paramincomplete)})
    response.status_code = 409
    response.headers['X-DIEGO'] = paramincomplete
    return response

@app.route('/v2/service_instances/<int:instance_id>/service_bindings/<int:binding_id>',  methods=['DELETE'])
def unbinding(instance_id,binding_id):
    response = jsonify({'description':'recu unbind service_instance %d, plan_id %d => pas implemente' % (instance_id, binding_id)})
    response.status_code = 503
    return response

@app.route('/v2/service_instances/<int:instance_id>', methods=['DELETE'])
def deprovisioning(instance_id):
    response = jsonify(myUnsupportedOperation)
    response.status_code = 503
    return response


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0', port=cfPort)
