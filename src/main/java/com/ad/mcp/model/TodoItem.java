/**
 * 
 */
package com.ad.mcp.model;

/**
 * 
 */

import java.time.Instant;

public record TodoItem(
    Long id,
    String title,
    boolean completed,
    Instant createdAt
) {}
