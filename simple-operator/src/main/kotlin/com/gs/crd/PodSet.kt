package com.gs.crd

import io.fabric8.kubernetes.client.CustomResource

class PodSet(
        val podSetSpec: PodSetSpec,
        val podSetStatus: PodSetStatus
): CustomResource() {

}