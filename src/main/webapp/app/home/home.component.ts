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
		      rotationalExternalShapes: [this.rotationalExternalShapes[1].value],
	          nonRotationalExternalShapes: [this.nonRotationalExternalShapes[1].value],
	          steppedOneEndInternalShapes: [this.steppedOneEndInternalShapes[1].value],
	          steppedBothEndInternalShapes: [this.steppedBothEndInternalShapes[1].value],
	          hollowInternalShapes: [this.hollowInternalShapes[1].value]
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

    public rotationalExternalShapes = [
      { value: 'CIRCULARDISCTYPE', display: 'Circular/Disc Type' },
      { value: 'CYLINDRICAL', display: 'Cylindrical' },
      { value: 'RECTANGULAR', display: 'Rectangular' },
      { value: 'TRIANGULAR', display: 'Triangular' },
      { value: 'IRREGULAR', display: 'Irregular' },
    ];
    public nonRotationalExternalShapes = [
	   { value: 'CIRCULARDISCTYPE', display: 'Circular/Disc Type' },
	   { value: 'CYLINDRICAL', display: 'Cylindrical' },
	   { value: 'RECTANGULAR', display: 'Rectangular' },
	   { value: 'TRIANGULAR', display: 'Triangular' },
	   { value: 'IRREGULAR', display: 'Irregular' },
	 ];
    
    public steppedOneEndInternalShapes = [
       { value: 'CIRCULARDISCTYPE', display: 'Circular/Disc Type' },
       { value: 'CYLINDRICAL', display: 'Cylindrical' },
       { value: 'RECTANGULAR', display: 'Rectangular' },
       { value: 'IRREGULAR', display: 'Irregular' },
     ];
     public steppedBothEndInternalShapes = [
 	   { value: 'CYLINDRICAL', display: 'Cylindrical' },
 	   { value: 'RECTANGULAR', display: 'Rectangular' },
 	   { value: 'IRREGULAR', display: 'Irregular' },
 	 ];
     public hollowInternalShapes = [
 	   { value: 'CYLINDRICAL', display: 'Cylindrical' },
 	   { value: 'RECTANGULAR', display: 'Rectangular' },
 	   { value: 'IRREGULAR', display: 'Irregular' },
 	 ];

    save(isValid: boolean, f: PartCode) {
      if (!isValid) return;
      console.log(f);
    }
}

export interface PartCode {
      rotationalDimensionChar?: number;
      steppedBothEndDimensionChar?: number;
      noOfHoles?: number;
      mass?: number;
	  rotationalExternalShapes?: string[];
	  nonRotationalExternalShapes?: string[];
	  steppedOneEndInternalShapes?: string[];
	  steppedBothEndInternalShapes?: string[];
	  hollowInternalShapes?: string[];
}
