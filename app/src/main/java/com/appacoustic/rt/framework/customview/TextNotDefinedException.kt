package com.appacoustic.rt.framework.customview

class TextNotDefinedException : Exception(
    "The 'custom_text' attribute has not been defined." +
        " Please, do it to ensure a proper UX.\n" +
        "This check is to ensure this attribute is filled as soon as possible and avoid UI errors."
)
