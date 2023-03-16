package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.GenerationDate;
import com.generic.khatabook.entity.Khatabook;
import com.generic.khatabook.model.KhatabookDTO;

public class KhatabookMapper {

    public static KhatabookDTO mapToPojo(Khatabook myKhatabook) {

        if (myKhatabook == null) {
            return null;
        }
        return new KhatabookDTO(myKhatabook.getBookId(), myKhatabook.getKhatabookId(), myKhatabook.getMsisdn(), myKhatabook.getPartnerName(), myKhatabook.getPartnerDescription());
    }

    public static Khatabook mapToDTO(KhatabookDTO myKhatabookDTO, final GenerationDate generationDate) {
        if (myKhatabookDTO == null) {
            return null;
        }
        return Khatabook.builder()
                .bookId(myKhatabookDTO.bookId())
                .khatabookId(myKhatabookDTO.khatabookId())
                .createdOn(generationDate.createdOn())
                .updatedOn(generationDate.updatedOn())
                .deletedOn(generationDate.deletedOn())
                .partnerName(myKhatabookDTO.partnerName())
                .partnerDescription(myKhatabookDTO.partnerDescription())
                .msisdn(myKhatabookDTO.msisdn())
                .build();
    }
}
