package com.example.repaircalculator.data.entity

import com.example.repaircalculator.R

enum class Status(val resource: Int, val color: Int) {
    IN_PROGRESS(R.string.in_progress, R.color.in_progress),
    FREEZE(R.string.freeze, R.color.freeze),
    FINISHED(R.string.finished, R.color.finished),
    NOT_STARTED(R.string.not_started, R.color.not_started),
}
