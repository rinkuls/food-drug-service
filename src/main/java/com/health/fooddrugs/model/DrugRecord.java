package com.health.fooddrugs.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "DrugRecord")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrugRecord {

    @Id
    private String applicationNumber;

    @NotBlank
    private String manufacturerName;

    @NotBlank
    private String substanceName;


}