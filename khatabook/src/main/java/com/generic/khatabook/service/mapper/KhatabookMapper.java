package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.entity.GenerationDate;
import com.generic.khatabook.entity.Khatabook;
import com.generic.khatabook.model.KhatabookDTO;
import org.springframework.stereotype.Component;

@Component
public class KhatabookMapper implements Mapper<Khatabook, KhatabookDTO, Void> {

    @Override
    public Khatabook mapToEntity(final KhatabookDTO khatabookDTO) {
        return mapToEntity(khatabookDTO, GenerationDate.creation());
    }

    @Override
    public Container<KhatabookDTO, Void> mapToContainer(final Khatabook khatabook) {
        return null;
    }

    public KhatabookDTO mapToDTO(Khatabook myKhatabook) {

        if (myKhatabook == null) {
            return null;
        }
        return new KhatabookDTO(null, myKhatabook.getBookId(), myKhatabook.getKhatabookId(), myKhatabook.getMsisdn(), myKhatabook.getPartnerName(), myKhatabook.getPartnerDescription());
    }

    public Khatabook mapToEntity(KhatabookDTO myKhatabookDTO, final GenerationDate generationDate) {
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
