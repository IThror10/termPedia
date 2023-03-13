import {Api} from "@/services/ApiService";
import {AxiosError} from "axios";
import {ErrorResponse} from "@/services/ErrorResponse";

export interface TermData {
    tid: number,
    name: string,
    description: string
}

export interface CreateTerm {
    name: string,
    description: string
}

export interface TermsArray {
    terms: TermData[]
}

export class TermService {
    public async getTerms(query: string): Promise<TermsArray> {
        const api = new Api();
        return await api.get<TermsArray>('/api/v1/terms?' + query);
    }

    async addTerm(addTerm: CreateTerm): Promise<ErrorResponse | null> {
        try {
            const api = new Api();
            await api.post('/api/v1/terms', addTerm);
            return null;
        } catch (e) {
            const error = e as AxiosError;
            if (error.response != null)
                return error.response.data as ErrorResponse;
            throw e;
        }
    }

    async getTermById(id: number): Promise<TermData | null> {
        try {
            const api = new Api();
            return await api.get<TermData>('/api/v1/terms/' + id);
        } catch (e) {
            const error = e as AxiosError;
            if (error.response != null && error.status === 404)
                return null;
            throw e;
        }
    }
}