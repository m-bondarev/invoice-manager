#resource "oci_identity_policy" "function_policy" {
#  compartment_id = var.tenancy_ocid
#  description    = "for Function"
#  name           = "function-pol"
#  statements     = [
#    "ALLOW any-user to use functions-family in compartment  ${var.compartment_name} where ALL {request.principal.type= 'ApiGateway', request.resource.compartment.id = '${var.compartment_ocid}'"
#  ]
#}
#
#resource "oci_identity_policy" "bucket_policy" {
#  compartment_id = ""
#  description    = ""
#  name           = ""
#  statements     = ["Allow group 'Default'/'All Domain Users' to read buckets in compartment ${var.compartment_name}"]
#}
#variable "compartment_name" {
#  default = "maryTest"
#}