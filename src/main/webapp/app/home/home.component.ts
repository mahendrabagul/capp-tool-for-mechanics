import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';
import * as FileSaver from "file-saver";
import { Account, LoginModalService, Principal } from '../shared';
import { PartService } from '../entities/part';

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
        private eventManager: EventManager,
        private partService: PartService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.partCodeResponse = {
        	partName:"",
        	partCode:""
        };
        this.partCode = {
    		externalShape: this.rotationalExternalShapes[0].value,
    		externalShapeType: this.externalShapeTypes[0].value,
    		internalShapeType: this.internalShapeTypes[0].value,
    		dimensionalCharacteristicsType: this.dimensionalCharacteristicsTypes[0].value,
    		internalShape: this.steppedOneEndInternalShapes[1].value,
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
    public partCodeResponse: PartCodeResponse;
    public externalShapeTypes = [
      { value: 'EXT_SHAPE_ROTATIONAL', display: 'Rotational' },
      { value: 'EXT_SHAPE_NON_ROTATIONAL', display: 'Non Rotational' }
    ];
    public nonRotationalExternalShapes = [
	  { value: 'EX_NON_ROT_CIRCULAR_DISC_TYPE', display: 'Circular/Disc Type', type:'EXT_SHAPE_NON_ROTATIONAL'},
	  { value: 'EX_NON_ROT_CYLINDRICAL', display: 'Cylindrical' , type:'EXT_SHAPE_NON_ROTATIONAL'},
	  { value: 'EX_NON_ROT_RECTANGULAR', display: 'Rectangular' , type:'EXT_SHAPE_NON_ROTATIONAL'},
	  { value: 'EX_NON_ROT_TRIANGULAR', display: 'Triangular' , type:'EXT_SHAPE_NON_ROTATIONAL'},
	  { value: 'EX_NON_ROT_IRREGULAR', display: 'Irregular' , type:'EXT_SHAPE_NON_ROTATIONAL'},
    ];
    public rotationalExternalShapes = [
   	  { value: 'EX_ROT_CIRCULAR_DISC_TYPE', display: 'Circular/Disc Type', type:'EXT_SHAPE_ROTATIONAL' },
   	  { value: 'EX_ROT_CYLINDRICAL', display: 'Cylindrical' , type:'EXT_SHAPE_ROTATIONAL'},
   	  { value: 'EX_ROT_RECTANGULAR', display: 'Rectangular', type:'EXT_SHAPE_ROTATIONAL'},
   	  { value: 'EX_ROT_TRIANGULAR', display: 'Triangular' , type:'EXT_SHAPE_ROTATIONAL'},
   	  { value: 'EX_ROT_IRREGULAR', display: 'Irregular' , type:'EXT_SHAPE_ROTATIONAL'},
    ];
    public internalShapeTypes = [
      { value: 'INT_SHAPE_STEPPED_ON_ONE_END', display: 'Stepped/Flanged On One End' },
      { value: 'INT_SHAPE_STEPPED_ON_BOTH_END', display: 'Steeped/Flanged On Both End' },
      { value: 'INT_SHAPE_HOLLOW', display: 'Hallow' }
    ];
	public steppedOneEndInternalShapes = [
       { value: 'INT_STEP_ONE_CIRCULAR_DISC_TYPE', display: 'Circular/Disc Type', type:"INT_SHAPE_STEPPED_ON_ONE_END" },
       { value: 'INT_STEP_ONE_CYLINDRICAL', display: 'Cylindrical', type:"INT_SHAPE_STEPPED_ON_ONE_END" },
       { value: 'INT_STEP_ONE_RECTANGULAR', display: 'Rectangular' , type:"INT_SHAPE_STEPPED_ON_ONE_END"},
       { value: 'INT_STEP_ONE_IRREGULAR', display: 'Irregular' , type:"INT_SHAPE_STEPPED_ON_ONE_END"},
     ];
     public steppedBothEndInternalShapes = [
 	   { value: 'INT_STEP_BOTH_CYLINDRICAL', display: 'Cylindrical', type:"INT_SHAPE_STEPPED_ON_BOTH_END"},
 	   { value: 'INT_STEP_BOTH_RECTANGULAR', display: 'Rectangular' ,type:"INT_SHAPE_STEPPED_ON_BOTH_END" },
 	   { value: 'INT_STEP_BOTH_IRREGULAR', display: 'Irregular' ,type:"INT_SHAPE_STEPPED_ON_BOTH_END" },
 	 ];
     public hollowInternalShapes = [
 	   { value: 'INT_HOL_CYLINDRICAL', display: 'Cylindrical', type:"INT_SHAPE_HOLLOW" },
 	   { value: 'INT_HOL_RECTANGULAR', display: 'Rectangular', type:"INT_SHAPE_HOLLOW" },
 	   { value: 'INT_HOL_IRREGULAR', display: 'Irregular', type:"INT_SHAPE_HOLLOW"},
 	 ];
     public dimensionalCharacteristicsTypes = [
 	   { value: 'DIM_ROTAOTIONAL_PARTS', display: 'Rotational Parts' },
 	   { value: 'DIM_STEPPED_ON_BOTH_END', display: 'Stepped/Flanged On Both End' }
 	 ];

     changeDropDownByShapeType(shapeType:string) {
    	if(shapeType=='EXT_SHAPE_ROTATIONAL'){
    		this.partCode.externalShape= this.rotationalExternalShapes[0].value;
    	}
    	else if(shapeType=='EXT_SHAPE_NON_ROTATIONAL'){
    		this.partCode.externalShape= this.nonRotationalExternalShapes[3].value;
    	}
    	else if(shapeType=='INT_SHAPE_STEPPED_ON_ONE_END'){
    		this.partCode.internalShape= this.steppedOneEndInternalShapes[0].value;
    	}
    	else if(shapeType=='INT_SHAPE_STEPPED_ON_BOTH_END'){
    		this.partCode.internalShape= this.steppedBothEndInternalShapes[1].value;
    	}
    	else if(shapeType=='INT_SHAPE_HOLLOW'){
    		this.partCode.internalShape= this.hollowInternalShapes[2].value;
    	}
    }
    getExternalShapeType() {
        return this.partCode.externalShapeType;
    }
    getInternalShapeType() {
        return this.partCode.internalShapeType;
    }
    searchPartCode(isValid: boolean, partCode: PartCode, form: any) {
        if (!isValid) return;
        this.partService.searchPartCode(JSON.stringify(partCode)).subscribe((response) => {
        	this.partCodeResponse.partName=response.partName;
        	this.partCodeResponse.partCode=response.partCode;
        	form.controls['noOfHoles'].reset();
        	form.controls['mass'].reset();
        	form.controls['dimensionalCharacteristic'].reset();
        });
    }
    downloadPartAttachment(partName) {
        this.partService.downloadPartAttachment(partName).subscribe((partAttachment) => {
        	let filename = partName + '.docx';
        	FileSaver.saveAs(partAttachment, filename);	
        });
    }
}

export interface PartCodeResponse {
	partName: string;
	partCode: string;
}

export interface PartCode {
  noOfHoles?: number;
  mass?: number;
  externalShapeType?: string;
  externalShape?: string;
  internalShapeType?: string;
  internalShape?: string;    
  dimensionalCharacteristicsType?: string; 
  dimensionalCharacteristic?: number;
}
