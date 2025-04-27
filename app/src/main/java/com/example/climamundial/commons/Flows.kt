package com.example.climamundial.commons

import kotlinx.coroutines.flow.Flow

/**
 * Combines  flows into a single flow by combining their latest values using the provided transform function.
 *
 * @param flow The first flow.
 * @param flow2 The second flow.
 * @param transform The transform function to combine the latest values of the seven flows.
 * @return A flow that emits the results of the transform function applied to the latest values of the seven flows.
 */
fun <T1, T2, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    transform: suspend (T1, T2) -> R
): Flow<R> =
    kotlinx.coroutines.flow.combine(
        flow,
        flow2,
    ) { args: Array<*> ->
        transform(
            args[0] as T1,
            args[1] as T2,
        )
    }
