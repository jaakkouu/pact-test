import { Application, Configuration } from "../generated";
import { DefaultApi } from "../generated/apis/DefaultApi";


export class ApplicationApi {

    private apiClient;

    constructor(){
        this.apiClient = new DefaultApi(new Configuration({
            basePath: "http://localhost:8080"
        }))
    }

    public getApplications(): Promise<Application[]> {
        return this.apiClient.getApplications();
    }

}