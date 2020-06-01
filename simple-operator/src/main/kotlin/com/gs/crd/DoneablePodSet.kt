package com.gs.crd

import io.fabric8.kubernetes.client.CustomResourceDoneable

class DoneablePodSet (
        resource: PodSet,
        function: io.fabric8.kubernetes.api.builder.Function<PodSet, PodSet>
): CustomResourceDoneable<PodSet>(
        resource,
        function
) {

}