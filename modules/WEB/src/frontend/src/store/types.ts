import {UserData} from "@/services/UserService";

export interface AuthState {
    isAuth: boolean,
    token: string,
    user: UserData | null
}

export interface StoreState {
    auth: AuthState
}