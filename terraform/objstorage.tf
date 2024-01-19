data "oci_objectstorage_namespace" "namespace" {
  compartment_id = var.compartment_ocid
}

resource "oci_objectstorage_bucket" "manager_bucket" {
  compartment_id = var.compartment_ocid
  name           = var.inv_manager_bucket.bucket_name
  namespace      = data.oci_objectstorage_namespace.namespace.namespace

  storage_tier          = "Standard"
  object_events_enabled = true
  auto_tiering          = "InfrequentAccess"
  versioning            = "Enabled"
  access_type           = "NoPublicAccess"
  lifecycle {
    prevent_destroy = false
  }
}

resource "oci_objectstorage_object" "manager_bucket_object" {
  bucket       = var.inv_manager_bucket.bucket_name
  content_md5  = "1B2M2Y8AsgTpgAmY7PhCfg=="
  content_type = "application/x-directory"
  metadata = {
    "virtual-folder-directory-createdby" = "console"
    "virtual-folder-directory-object"    = "true"
  }
  namespace    = data.oci_objectstorage_namespace.namespace.namespace
  object       = var.inv_manager_bucket.prefix
  storage_tier = "Standard"
  depends_on   = [oci_objectstorage_bucket.manager_bucket]
}

resource "oci_objectstorage_object_lifecycle_policy" "backup_terraformBackup_object_lifecycle_policy" {
  bucket    = var.inv_manager_bucket.bucket_name
  namespace = data.oci_objectstorage_namespace.namespace.namespace
  rules {
    action      = "ABORT"
    is_enabled  = "true"
    name        = "manager-lifecycle-rule"
    target      = "multipart-uploads"
    time_amount = "7"
    time_unit   = "DAYS"
  }
  rules {
    action     = "DELETE"
    is_enabled = "true"
    name       = "Delete prev obj versions rule"
    object_name_filter {
      inclusion_prefixes = [
        var.inv_manager_bucket.prefix
      ]
    }
    target      = "previous-object-versions"
    time_amount = "1"
    time_unit   = "DAYS"
  }
  depends_on = [oci_objectstorage_bucket.manager_bucket]
}
data "oci_objectstorage_object_versions" "manager_object_versions" {
  bucket    = var.inv_manager_bucket.bucket_name
  namespace = data.oci_objectstorage_namespace.namespace.namespace

  prefix      = var.inv_manager_bucket.prefix
  fields      = "timeModified"
  start       = "1"
  start_after = "1"
  depends_on  = [oci_objectstorage_bucket.manager_bucket]
}

resource "oci_objectstorage_preauthrequest" "manage_preauth_request" {
  access_type           = "AnyObjectRead"
  bucket                = var.inv_manager_bucket.bucket_name
  bucket_listing_action = "ListObjects"
  name                  = "Get terraform state"
  namespace             = data.oci_objectstorage_namespace.namespace.namespace
  object_name           = var.inv_manager_bucket.prefix
  time_expires          = "2024-01-30T15:00:04.6Z"
  depends_on            = [oci_objectstorage_bucket.manager_bucket]
}
