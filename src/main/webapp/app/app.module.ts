import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';
import { FormsModule } from '@angular/forms';
import { VariantCappToolSharedModule, UserRouteAccessService } from './shared';
import { VariantCappToolHomeModule } from './home/home.module';
import { VariantCappToolAdminModule } from './admin/admin.module';
import { VariantCappToolAccountModule } from './account/account.module';
import { VariantCappToolEntityModule } from './entities/entity.module';
// import { FileDropDirective, FileSelectDirective } from 'ng2-file-upload';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        VariantCappToolSharedModule,
        VariantCappToolHomeModule,
        VariantCappToolAdminModule,
        VariantCappToolAccountModule,
        VariantCappToolEntityModule,
        FormsModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
		// FileSelectDirective,
		// FileDropDirective
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class VariantCappToolAppModule {}
