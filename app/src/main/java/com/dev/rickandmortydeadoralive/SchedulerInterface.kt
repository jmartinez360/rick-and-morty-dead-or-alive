package com.dev.rickandmortydeadoralive

import io.reactivex.Scheduler

interface SchedulerInterface {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}