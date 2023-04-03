import {Api} from "@/services/ApiService";
import {ErrorResponse} from "@/services/ErrorResponse";
import {AxiosError} from "axios";

export interface LiteratureData {
    lid: number,
    name: string,
    year: number,
    type: string,
    authors: string[]
}

export interface TagLiteratureData extends LiteratureData {
    rating: number
}

export interface RatedLiteratureData extends TagLiteratureData {
    ratesAmount: number
}


export interface LitRating {
    termId: number,
    id: number,
    mark: number
}

export interface LiteratureArray {
    lit: LiteratureData[]
}

export interface TagLiteratureArray {
    lit: TagLiteratureData[]
}

export interface RatedLiteratureArray {
    lit: RatedLiteratureData[]
}

export class LiteratureService {

    public async createLit(form: LiteratureData): Promise<boolean | ErrorResponse> {
        try {
            const api = new Api();
            await api.post('/api/v1/literature', form);
            return true;
        } catch (e) {
            const error = e as AxiosError;
            if (error.response != null)
                return error.response.data as ErrorResponse;
            throw e;
        }
    }

    async connectToTerm(termId: number, lid: number): Promise<boolean> {
        const api = new Api();
        await api.post('/api/v1/literature/' + lid.toString() + '/terms', {termId: termId})
        return true;
    }

    public async searchByTermId(termId: number, query: string): Promise<RatedLiteratureArray> {
        const api = new Api();
        return await api.get<RatedLiteratureArray>('/api/v1/literature?term_id=' + termId + '&' + query);
    }

    public async getRating(litId: number, termId: number): Promise<number> {
        const api = new Api();
        const response = await api.get<LitRating>(`/api/v1/literature/${litId}/userRating?termId=${termId}`);
        return response.mark;
    }

    public async setRating(litId: number, termId: number, rating: number): Promise<ErrorResponse | boolean> {
        const api = new Api();
        await api.post(`/api/v1/literature/${litId}/userRating`,
            {termId: termId, mark: rating}
        );
        return true;
    }

    public async searchByName(query: string): Promise<LiteratureArray> {
        const api = new Api();
        const response = await api.get<LiteratureArray>('/api/v1/literature?' + query);
        return response;
    }

    async searchByTermName(query: string): Promise<TagLiteratureArray> {
        const api = new Api();
        return await api.get<TagLiteratureArray>('/api/v1/literature?' + query);
    }

    async searchByAuthor(query: string): Promise<LiteratureArray> {
        const api = new Api();
        return await api.get<LiteratureArray>('/api/v1/literature?' + query);
    }

    public async searchByTags(query: string): Promise<TagLiteratureArray> {
        const api = new Api();
        return await api.get<TagLiteratureArray>('/api/v1/literature?' + query);
    }
}