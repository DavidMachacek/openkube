variable "client_id" {
	default = "6d56736f-d32b-459a-9176-5535cfc53108"
}

variable "client_secret" {
	default = "bc0d68e0-1936-45ac-9049-7277fff567c4"
	}

variable "agent_count" {
    default = 2
}

variable "ssh_public_key" {
    default = "~/.ssh/id_rsa.pub"
}

variable "dns_prefix" {
    default = "my-cluster"
}

variable cluster_name {
    default = "my-cluster"
}

variable resource_group_name {
    default = "myrg"
}

variable location {
    default = "West Europe"
}

variable log_analytics_workspace_name {
    default = "mylogsdavid"
}

# refer https://azure.microsoft.com/pricing/details/monitor/ for log analytics pricing 
variable log_analytics_workspace_sku {
    default = "PerGB2018"
}

# refer https://azure.microsoft.com/global-infrastructure/services/?products=monitor for log analytics available regions
variable log_analytics_workspace_location {
    default = "West Europe"
}

variable owner {
	default = "David"
}