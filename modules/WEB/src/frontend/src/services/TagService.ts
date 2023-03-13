import {Api} from "@/services/ApiService";
import {ErrorResponse} from "@/services/ErrorResponse";
import {AxiosError} from "axios/index";

export interface TagData {
    name: string,
    rating: number,
    ratesAmount: number
}

export interface TagName {
    name: string
}

export interface TagRating {
    termId: number,
    id: string,
    mark: number
}

export interface TagsArray {
    tags: TagData[]
}

export interface TagsNameArray {
    tags: TagName[]
}

export class TagService {
    public async getTagsByTerm(tid: number, query: string): Promise<TagsArray> {
        const api = new Api();
        return await api.get<TagsArray>(`/api/v1/terms/${tid}/tags?${query}`);
    }

    public async getTagsByName(query: string): Promise<TagsNameArray> {
        const api = new Api();
        console.log(`/api/v1/tags?${query}`);
        return await api.get<TagsNameArray>(`/api/v1/tags?${query}`);
    }

    public async addTagToTerm(termId: number, searchName: string): Promise<ErrorResponse | boolean> {
        try {
            const api = new Api();
            await api.post(`/api/v1/terms/${termId}/tags`, {tagName: searchName});
            return true;
        } catch (e) {
            const error = e as AxiosError;
            if (error.response != null)
                return error.response.data as ErrorResponse;
            throw e;
        }
    }

    public async getRating(name: string, termId: number): Promise<number> {
        const api = new Api();
        const response = await api.get<TagRating>(`/api/v1/tags/${name}/userRating?tid_to_tag=` + termId.toString());
        return response.mark;
    }

    public async setRating(name: string, termId: number, rating: number): Promise<ErrorResponse | boolean> {
        const api = new Api();
        await api.post(`/api/v1/tags/${name}/userRating`,
            {termId: termId, mark: rating}
        );
        return true;
    }
}