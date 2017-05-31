import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';
// import the file uploader plugin
// import { FileUploader } from 'ng2-file-upload/ng2-file-upload';
// const URL = 'http://localhost:5454/api/upload/multi';
import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Part } from './part.model';
import { PartPopupService } from './part-popup.service';
import { PartService } from './part.service';

@Component({
    selector: 'jhi-part-dialog',
    templateUrl: './part-dialog.component.html'
})
export class PartDialogComponent implements OnInit {

    part: Part;
    authorities: any[];
    isSaving: boolean;

	// public uploader:FileUploader = new FileUploader({url: URL, itemAlias:
	// 'files'});
	
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private partService: PartService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];

		// this.uploader.onAfterAddingFile = (file)=> { file.withCredentials =
		// false; };
		// this.uploader.onCompleteItem = (item:any, response:any, status:any,
		// headers:any) => {
		// console.log("ImageUpload:uploaded:", item, status, response);
		// };
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.part.id !== undefined) {
            this.subscribeToSaveResponse(
                this.partService.update(this.part));
        } else {
            this.subscribeToSaveResponse(
                this.partService.create(this.part));
        }
    }

    private subscribeToSaveResponse(result: Observable<Part>) {
        result.subscribe((res: Part) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Part) {
        this.eventManager.broadcast({ name: 'partListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-part-popup',
    template: ''
})
export class PartPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partPopupService: PartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.partPopupService
                    .open(PartDialogComponent, params['id']);
            } else {
                this.modalRef = this.partPopupService
                    .open(PartDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
