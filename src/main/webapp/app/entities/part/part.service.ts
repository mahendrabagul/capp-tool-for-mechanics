import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Part } from './part.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PartService {

    private resourceUrl = 'api/parts';

    constructor(private http: Http) { }

    create(part: Part): Observable<Part> {
        const copy = this.convert(part);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(part: Part): Observable<Part> {
        const copy = this.convert(part);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Part> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    downloadPartAttachment(partName: String): Observable<Part> {
    	let headers = new Headers({ 
		   'Content-Type': 'application/json'
    	});
    	let body = {}
    	let options = new RequestOptions({ headers: headers });
    	options.responseType = ResponseContentType.Blob;
        return this.http.post(`${this.resourceUrl}/download/${partName}`, body, options).map((response: Response) => {
        	 let fileBlob = response.blob();
             let blob = new Blob([fileBlob]);
             return blob;
        });
    }

    searchPartCode(partCodeData: string): Observable<Part> {
    	let body = JSON.parse(partCodeData);
    	let headers = new Headers({ 
 		   'Content-Type': 'application/json'
     	});
    	let options = new RequestOptions({ headers: headers });
    	console.log(body);
        return this.http.post(`${this.resourceUrl}/searchPartCode`, body, options).map((response: Response) => {
             return response.json();
        });
    }
    
    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(part: Part): Part {
        const copy: Part = Object.assign({}, part);
        return copy;
    }
}
