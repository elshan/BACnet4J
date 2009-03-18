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
import com.serotonin.bacnet4j.type.Encodable;
import com.serotonin.bacnet4j.type.constructed.StatusFlags;
import com.serotonin.util.queue.ByteQueue;

public class CommandFailure extends NotificationParameters {
    public static final byte TYPE_ID = 3;
  
    private Encodable commandValue;
    private final StatusFlags statusFlags;
    private Encodable feedbackValue;
  
    public CommandFailure(Encodable commandValue, StatusFlags statusFlags, Encodable feedbackValue) {
        this.commandValue = commandValue;
        this.statusFlags = statusFlags;
        this.feedbackValue = feedbackValue;
    }
    
    @Override
    protected void writeImpl(ByteQueue queue) {
        write(queue, commandValue, 0);
        write(queue, statusFlags, 1);
        write(queue, feedbackValue, 2);
    }
    
    public CommandFailure(ByteQueue queue) throws BACnetException {
        // Not sure how to do this: commandValue.
        statusFlags = read(queue, StatusFlags.class, 1);
        // Not sure how to do this: feedbackValue.
    }

    @Override
    protected int getTypeId() {
        return TYPE_ID;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((commandValue == null) ? 0 : commandValue.hashCode());
        result = PRIME * result + ((feedbackValue == null) ? 0 : feedbackValue.hashCode());
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
        final CommandFailure other = (CommandFailure) obj;
        if (commandValue == null) {
            if (other.commandValue != null)
                return false;
        }
        else if (!commandValue.equals(other.commandValue))
            return false;
        if (feedbackValue == null) {
            if (other.feedbackValue != null)
                return false;
        }
        else if (!feedbackValue.equals(other.feedbackValue))
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
