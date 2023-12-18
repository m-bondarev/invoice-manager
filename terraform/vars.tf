variable "user_ocid" {
}
variable "fingerprint" {
}
variable "tenancy_ocid" {
}
variable "region" {
}
variable "private_key" {
}
variable "compartment_ocid" {
}

#network variables
variable "vcn_subnet_cidr_blocks" {
  default = ["10.0.0.0/16"]
}
variable "private_subnet_cidr_block" {
  default = "10.0.1.0/24"
}
variable "public_subnet_cidr_block" {
  default = "10.0.2.0/24"
}

#storage variables
variable "inv_manager_bucket" {
  type = map(string)
  default = {
    bucket_name = "invoice_manager_bucket"
    prefix      = "storage/"
  }
}

#back variables
variable "image_id" {
  default = "ocid1.image.oc1.eu-frankfurt-1.aaaaaaaao27f22d7avekl642gfes2ijwezsuupwhtteh4wwyjwwgvyxzlzcq"
}
variable "ssh_author_key" {
  default = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCWrCZglz6oSAZXiT+lHjBLqS1mpZeoN42NPfOoFIg6+ivACHlbQPpOLtXfE5OWw9qXsFxb+vsk5Od6KpYaGaodPCZ+AoFH6Qsz1PhUqQ7gXzcb6S77hSHge+070I3QIYyqPfTlSfKDxkJ0DHIX6uhpnEWj+1knvBkISlPsEDdu7xkloNbHoBZlVP0n7zAwXXVzrz9tMUnPna3XdpSGoTZzYxsFhb5iNAwVa3zZ3qIV6+lSqrBj4oNnTUYWIs75e9vkrUyXwQTcqVk/wb5V8rIKOem72Vi91RnYHbKJe2id5bPxGDoy7tUlGN6X55Cry3QrfX0GcmUS1OSGbb/d3KAp ssh-key-2023-12-13"
}