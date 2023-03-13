import {TokenUserData, UserData} from "@/services/UserService";
import {Commit, Module} from "vuex";
import {AuthState, StoreState} from "@/store/types";

export const authModule: Module<AuthState, StoreState> = {
    state: (): AuthState => ({
        isAuth: false,
        token: '',
        user: null
    }),
    mutations: {
        SET_IS_AUTH(state: AuthState, isAuth: boolean) {
            state.isAuth = isAuth;
        },
        SET_TOKEN(state: AuthState, token: string) {
            state.token = token;
        },
        SET_DATA(state: AuthState, user: UserData | null) {
            state.user = user;
        }
    },
    actions: {
        loggedIn({commit}: {commit: Commit}, {inData}: {inData: TokenUserData}) {
            commit('SET_IS_AUTH', true);
            commit('SET_TOKEN', inData.token);
            commit('SET_DATA', inData.user);
        },
        loggedOut({commit}: {commit: Commit}) {
            commit('SET_IS_AUTH', false);
            commit('SET_TOKEN', '');
            commit('SET_DATA', null);
        },
        dataChanged({commit}: {commit: Commit}, {inData} : {inData: UserData}) {
            commit('SET_DATA', inData)
        }
    },
    getters: {
        isAuth: (state: AuthState) => state.isAuth,
        token: (state: AuthState) => state.token,
        user: (state: AuthState) => state.user
    },
    namespaced: true
}