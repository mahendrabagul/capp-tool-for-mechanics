import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';
import * as FileSaver from "file-saver";
import { Part } from './part.model';
import { PartService } from './part.service';

@Component({
    selector: 'jhi-part-detail',
    templateUrl: './part-detail.component.html'
})
export class PartDetailComponent implements OnInit, OnDestroy {

    part: Part;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private partService: PartService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParts();
    }

    load(id) {
        this.partService.find(id).subscribe((part) => {
            this.part = part;
        });
    }
    downloadPartAttachment(partName) {
        this.partService.downloadPartAttachment(partName).subscribe((partAttachment) => {
        	let filename = partName + '.docx';
        	FileSaver.saveAs(partAttachment, filename);	
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'partListModification',
            (response) => this.load(this.part.id)
        );
    }
}
