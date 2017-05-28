import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VariantCappToolSharedModule } from '../../shared';
import {
    PartService,
    PartPopupService,
    PartComponent,
    PartDetailComponent,
    PartDialogComponent,
    PartPopupComponent,
    PartDeletePopupComponent,
    PartDeleteDialogComponent,
    partRoute,
    partPopupRoute,
    PartResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...partRoute,
    ...partPopupRoute,
];

@NgModule({
    imports: [
        VariantCappToolSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PartComponent,
        PartDetailComponent,
        PartDialogComponent,
        PartDeleteDialogComponent,
        PartPopupComponent,
        PartDeletePopupComponent,
    ],
    entryComponents: [
        PartComponent,
        PartDialogComponent,
        PartPopupComponent,
        PartDeleteDialogComponent,
        PartDeletePopupComponent,
    ],
    providers: [
        PartService,
        PartPopupService,
        PartResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VariantCappToolPartModule {}
