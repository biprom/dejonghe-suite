package com.adverto.dejonghe.common.entities.product.product;

import com.adverto.dejonghe.common.wizard.WIZARDTYPE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Wizard implements Serializable {
    WIZARDTYPE wizardtype;
    Double rho;
    Double mkg;
}
