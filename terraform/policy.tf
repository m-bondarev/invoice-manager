#resource "oci_identity_policy" "function_test_policy" {
#  compartment_id = var.tenancy_ocid
#  description    = "for Function"
#  name           = "function-pol"
#  statements     = [
#    "ALLOW any-user to use functions-family in compartment maryTest where ALL {request.principal.type= 'ApiGateway', request.resource.compartment.id = '${var.compartment_ocid}'"
#  ]
#}