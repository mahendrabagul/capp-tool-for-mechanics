import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        
        this.partCode = {
    		externalShapes: this.rotationalExternalShapes[0].value,
    		externalShapeType: this.externalShapeTypes[0].value,
    		internalShapeType: this.internalShapeTypes[0].value,
    		dimensionalCharacteristicsType: this.dimensionalCharacteristicsTypes[0].value,
    		internalShapes: this.steppedOneEndInternalShapes[1].value,
        }
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
    
    //
    public partCode: PartCode;
    public externalShapeTypes = [
      { value: 'EXT_SHAPE_ROTATIONAL', display: 'Rotational' },
      { value: 'EXT_SHAPE_NON_ROTATIONAL', display: 'Non Rotational' }
    ];
    public nonRotationalExternalShapes = [
	  { value: 'EX_NON_ROT_CIRCULAR_DISC_TYPE', display: 'Circular/Disc Type' },
	  { value: 'EX_NON_ROT_CYLINDRICAL', display: 'Cylindrical' },
	  { value: 'EX_NON_ROT_RECTANGULAR', display: 'Rectangular' },
	  { value: 'EX_NON_ROT_TRIANGULAR', display: 'Triangular' },
	  { value: 'EX_NON_ROT_IRREGULAR', display: 'Irregular' },
    ];
    public rotationalExternalShapes = [
   	  { value: 'EX_ROT_CIRCULAR_DISC_TYPE', display: 'Circular/Disc Type' },
   	  { value: 'EX_ROT_CYLINDRICAL', display: 'Cylindrical' },
   	  { value: 'EX_ROT_RECTANGULAR', display: 'Rectangular' },
   	  { value: 'EX_ROT_TRIANGULAR', display: 'Triangular' },
   	  { value: 'EX_ROT_IRREGULAR', display: 'Irregular' },
    ];
    public internalShapeTypes = [
      { value: 'INT_SHAPE_STEPPED_ON_ONE_END', display: 'Stepped/Flanged On One End' },
      { value: 'INT_SHAPE_STEPPED_ON_BOTH_END', display: 'Steeped/Flanged On Both End' },
      { value: 'INT_SHAPE_HOLLOW', display: 'Hallow' }
    ];
	public steppedOneEndInternalShapes = [
       { value: 'INT_STEP_ONE_CIRCULAR_DISC_TYPE', display: 'Circular/Disc Type' },
       { value: 'INT_STEP_ONE_CYLINDRICAL', display: 'Cylindrical' },
       { value: 'INT_STEP_ONE_RECTANGULAR', display: 'Rectangular' },
       { value: 'INT_STEP_ONE_IRREGULAR', display: 'Irregular' },
     ];
     public steppedBothEndInternalShapes = [
 	   { value: 'INT_STEP_BOTH_CYLINDRICAL', display: 'Cylindrical' },
 	   { value: 'INT_STEP_BOTH_RECTANGULAR', display: 'Rectangular' },
 	   { value: 'INT_STEP_BOTH_IRREGULAR', display: 'Irregular' },
 	 ];
     public hollowInternalShapes = [
 	   { value: 'INT_HOL_CYLINDRICAL', display: 'Cylindrical' },
 	   { value: 'INT_HOL_RECTANGULAR', display: 'Rectangular' },
 	   { value: 'INT_HOL_IRREGULAR', display: 'Irregular' },
 	 ];
     public dimensionalCharacteristicsTypes = [
 	   { value: 'DIM_ROTAOTIONAL_PARTS', display: 'Rotational Parts' },
 	   { value: 'DIM_STEPPED_ON_BOTH_END', display: 'Stepped/Flanged On Both End' }
 	 ];
    save(isValid: boolean, f: PartCode) {
      if (!isValid) return;
      this.callMe(f);
    }
    callMe(f: PartCode){
      console.log(f);
    }
    getExternalShapeType() {
        return this.partCode.externalShapeType;
    }
    getInternalShapeType() {
        return this.partCode.internalShapeType;
    }
}

export interface PartCode {
      noOfHoles?: number;
      mass?: number;
      externalShapeType?: string;
      externalShapes?: string;
      internalShapeType?: string;
      internalShapes?: string;    
      dimensionalCharacteristicsTypes?: string[];
      dimensionalCharacteristicsType?: string; 
	  dimensionalCharacteristic?: number;
	  // steppedOneEndInternalShapes?: string[];
	  // steppedBothEndInternalShapes?: string[];
	  // hollowInternalShapes?: string[];
}
