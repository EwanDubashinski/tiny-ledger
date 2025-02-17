package com.example.tiny_ledger.vo;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionIdTests {
    @Test
    void generate_ShouldCreateNonNullTransactionId() {
        TransactionId transactionId = TransactionId.generate();
        assertNotNull(transactionId, "TransactionId.generate() should not return null");
        assertNotNull(transactionId.getValue(), "TransactionId's value should not be null");
    }

    @Test
    void equalsAndHashCode_ShouldWorkProperly() {
        UUID uuid = UUID.randomUUID();
        TransactionId id1 = createTransactionId(uuid);
        TransactionId id2 = createTransactionId(uuid);
        TransactionId id3 = TransactionId.generate(); // likely a different UUID

        // Instances with the same UUID should be equal and have the same hash code.
        assertEquals(id1, id2, "TransactionIds with the same UUID should be equal");
        assertEquals(id1.hashCode(), id2.hashCode(), "Hash codes should match for equal TransactionIds");

        // An instance with a different UUID should not be equal.
        assertNotEquals(id1, id3, "TransactionIds with different UUIDs should not be equal");
    }

    @Test
    void toString_ShouldContainUUIDValue() {
        UUID uuid = UUID.randomUUID();
        TransactionId transactionId = createTransactionId(uuid);
        String stringRepresentation = transactionId.toString();
        assertTrue(stringRepresentation.contains(uuid.toString()),
                "toString() should contain the UUID value");
    }

    /**
     * Helper method to create a TransactionId instance with a specified UUID.
     * Since the constructor is private, we use reflection.
     */
    private TransactionId createTransactionId(UUID uuid) {
        try {
            Constructor<TransactionId> constructor =
                    TransactionId.class.getDeclaredConstructor(UUID.class);
            constructor.setAccessible(true);
            return constructor.newInstance(uuid);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create TransactionId instance via reflection", e);
        }
    }
}
