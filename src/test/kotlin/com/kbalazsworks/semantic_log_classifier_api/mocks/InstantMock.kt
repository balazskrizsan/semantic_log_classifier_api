package com.kbalazsworks.semantic_log_classifier_api.mocks

import io.mockk.mockk
import java.time.Instant

class InstantMock {
    companion object {
        val fixTime = Instant.parse("2024-01-01T12:00:00Z")
    }
}
