package com.sample.simpsonsviewer.utils

class NullSimpsonsCharacterListResponse(
    message: String = "Simpson Character List Reponse is null"
): Exception(message)
class FailureResponse(message: String?): Exception(message)