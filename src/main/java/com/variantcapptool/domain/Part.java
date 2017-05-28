package com.variantcapptool.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Part.
 */
@Entity
@Table(name = "part")
public class Part implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "part_length_dimension")
    private Double partLengthDimension;

    @Column(name = "part_height_width_diameter")
    private Double partHeightWidthDiameter;

    @Column(name = "ratio_ld")
    private Double ratioLD;

    @Column(name = "no_of_holes")
    private Integer noOfHoles;

    @Column(name = "mass")
    private Integer mass;

    @Column(name = "part_code")
    private String partCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public Part partName(String partName) {
        this.partName = partName;
        return this;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Double getPartLengthDimension() {
        return partLengthDimension;
    }

    public Part partLengthDimension(Double partLengthDimension) {
        this.partLengthDimension = partLengthDimension;
        return this;
    }

    public void setPartLengthDimension(Double partLengthDimension) {
        this.partLengthDimension = partLengthDimension;
    }

    public Double getPartHeightWidthDiameter() {
        return partHeightWidthDiameter;
    }

    public Part partHeightWidthDiameter(Double partHeightWidthDiameter) {
        this.partHeightWidthDiameter = partHeightWidthDiameter;
        return this;
    }

    public void setPartHeightWidthDiameter(Double partHeightWidthDiameter) {
        this.partHeightWidthDiameter = partHeightWidthDiameter;
    }

    public Double getRatioLD() {
        return ratioLD;
    }

    public Part ratioLD(Double ratioLD) {
        this.ratioLD = ratioLD;
        return this;
    }

    public void setRatioLD(Double ratioLD) {
        this.ratioLD = ratioLD;
    }

    public Integer getNoOfHoles() {
        return noOfHoles;
    }

    public Part noOfHoles(Integer noOfHoles) {
        this.noOfHoles = noOfHoles;
        return this;
    }

    public void setNoOfHoles(Integer noOfHoles) {
        this.noOfHoles = noOfHoles;
    }

    public Integer getMass() {
        return mass;
    }

    public Part mass(Integer mass) {
        this.mass = mass;
        return this;
    }

    public void setMass(Integer mass) {
        this.mass = mass;
    }

    public String getPartCode() {
        return partCode;
    }

    public Part partCode(String partCode) {
        this.partCode = partCode;
        return this;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Part part = (Part) o;
        if (part.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), part.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Part{" +
            "id=" + getId() +
            ", partName='" + getPartName() + "'" +
            ", partLengthDimension='" + getPartLengthDimension() + "'" +
            ", partHeightWidthDiameter='" + getPartHeightWidthDiameter() + "'" +
            ", ratioLD='" + getRatioLD() + "'" +
            ", noOfHoles='" + getNoOfHoles() + "'" +
            ", mass='" + getMass() + "'" +
            ", partCode='" + getPartCode() + "'" +
            "}";
    }
}
