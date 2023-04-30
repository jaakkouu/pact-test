import { Application, Configuration } from "../generated";
import { DefaultApi } from "../generated/apis/DefaultApi";


export class ApplicationApi {

    private apiClient: DefaultApi;
    public basePath: string;

    constructor(private baseUri?: string){
        this.basePath = this.baseUri || "http://localhost:8080";
        this.apiClient = new DefaultApi(new Configuration({
            headers: {
                "Accept": "application/json",
            },
            basePath: this.basePath
        }));
    }

    public getApplications(): Promise<Application[]> {
        return this.apiClient.getApplications().catch((e) => {
            console.info(`Error occured when getting applications: ${e}`);
            return [];
        });
    }

}