package com.csw.dao;

import com.csw.model.LocalizedString;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalizedStringDAO {
    int insertData(@Param("localizedString") LocalizedString localizedString,
                    @Param("localizedStringId") String localizedStringId);

    List<LocalizedString> selectLocalizedStringsBy_Id(@Param("localizedStringId") String localizedStringId);

    List<LocalizedString> selectLocalizedStringsByFuzzIdAndValue(@Param("fuzzId")String fuzzId,
                                                                @Param("fuzzValue")String fuzzValue);

    int deleteLocalizedStringById(@Param("id") String id);
}
