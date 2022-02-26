package com.example.mynoteapp.Data

enum class Action {
    NONE,
    ADD,
    DELETE,
    DELETE_ALL,
    UPDATE,
    UNDO
}
fun String?.toAction():Action {
    return when {
        this=="ADD"->{
            Action.ADD
        }
        this=="UPDATE"->{
            Action.UPDATE
        }
        this=="DELETE"->{
            Action.DELETE
        }
        this=="DELETE_ALL"->{
            Action.DELETE_ALL
        }
        this=="UNDO"->{
            Action.UNDO
        }
        else->Action.NONE

    }
}