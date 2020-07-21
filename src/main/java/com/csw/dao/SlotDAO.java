package com.csw.dao;

import com.csw.model.Slot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SlotDAO {
    int insertData(@Param("slot") Slot slot,
                    @Param("slotId") String slotId);

    List<Slot> selectSlotsBy_Id(@Param("slotId") String slotId);

    List<Slot> selectSlotsByFuzzNameAndValue(@Param("fuzzName")String fuzzName,
                                                @Param("fuzzValue")String fuzzValue);

    List<Slot> selectSlotsByFuzzNameAndId(@Param("fuzzName")String fuzzName,
                                             @Param("fuzzSlotId")String fuzzSlotId);

    int deleteSlotById(@Param("id") String id);
}
