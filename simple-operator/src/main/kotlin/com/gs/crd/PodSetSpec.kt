package com.gs.crd

import io.fabric8.kubernetes.api.model.KubernetesResource

class PodSetSpec(
        val replicas: Int
): KubernetesResource {

    override fun toString(): String {
        return "PodSetSpec{replicas=$replicas}"
    }
}