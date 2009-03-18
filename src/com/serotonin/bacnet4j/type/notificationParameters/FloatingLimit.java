/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2009 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 */
package com.serotonin.bacnet4j.type.notificationParameters;

import com.serotonin.bacnet4j.exception.BACnetException;
import com.serotonin.bacnet4j.type.constructed.StatusFlags;
import com.serotonin.bacnet4j.type.primitive.Real;
import com.serotonin.util.queue.ByteQueue;

public class FloatingLimit extends NotificationParameters {
    public static final byte TYPE_ID = 4;
  
    private final Real referenceValue;
    private final StatusFlags statusFlags;
    private final Real setpointValue;
    private final Real errorLimit;
    
    public FloatingLimit(Real referenceValue, StatusFlags statusFlags, Real setpointValue, Real errorLimit) {
        this.referenceValue = referenceValue;
        this.statusFlags = statusFlags;
        this.setpointValue = setpointValue;
        this.errorLimit = errorLimit;
    }
    
    @Override
    protected void writeImpl(ByteQueue queue) {
        write(queue, referenceValue, 0);
        write(queue, statusFlags, 0);
        write(queue, setpointValue, 0);
        write(queue, errorLimit, 0);
    }
    
    public FloatingLimit(ByteQueue queue) throws BACnetException {
        referenceValue = read(queue, Real.class, 0);
        statusFlags = read(queue, StatusFlags.class, 1);
        setpointValue = read(queue, Real.class, 2);
        errorLimit = read(queue, Real.class, 3);
    }
    
    @Override
    protected int getTypeId() {
        return TYPE_ID;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((errorLimit == null) ? 0 : errorLimit.hashCode());
        result = PRIME * result + ((referenceValue == null) ? 0 : referenceValue.hashCode());
        result = PRIME * result + ((setpointValue == null) ? 0 : setpointValue.hashCode());
        result = PRIME * result + ((statusFlags == null) ? 0 : statusFlags.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FloatingLimit other = (FloatingLimit) obj;
        if (errorLimit == null) {
            if (other.errorLimit != null)
                return false;
        }
        else if (!errorLimit.equals(other.errorLimit))
            return false;
        if (referenceValue == null) {
            if (other.referenceValue != null)
                return false;
        }
        else if (!referenceValue.equals(other.referenceValue))
            return false;
        if (setpointValue == null) {
            if (other.setpointValue != null)
                return false;
        }
        else if (!setpointValue.equals(other.setpointValue))
            return false;
        if (statusFlags == null) {
            if (other.statusFlags != null)
                return false;
        }
        else if (!statusFlags.equals(other.statusFlags))
            return false;
        return true;
    }
}
