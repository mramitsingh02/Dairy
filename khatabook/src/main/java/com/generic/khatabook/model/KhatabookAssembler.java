package com.generic.khatabook.model;

import com.generic.khatabook.controller.KhatabookController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public final class KhatabookAssembler extends RepresentationModelAssemblerSupport<KhatabookDTO, KhatabookDetails> {
    public KhatabookAssembler() {
        super(KhatabookController.class, KhatabookDetails.class);
    }


    @Override
    public KhatabookDetails toModel(final KhatabookDTO entity) {
        return null;
    }
}
