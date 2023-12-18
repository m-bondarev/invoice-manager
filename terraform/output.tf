output "function_test" {
  value = "${oci_apigateway_deployment.function_api_gw_deployment.endpoint}${oci_apigateway_deployment.function_api_gw_deployment.specification[0].routes[0].path}"
}
output "list_all_objects_in_storage" {
  value = oci_objectstorage_preauthrequest.manage_preauth_request.full_path
}
