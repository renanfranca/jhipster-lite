import { GithubToken } from '@/module/domain/GithubToken';
import { AxiosResponse } from 'axios';

export interface RestGithubToken {
  access_token: string;
  token_type: string;
}

export const mapToGithubToken = (response: AxiosResponse<RestGithubToken>): GithubToken => {
  const data = response.data;
  return {
    accessToken: data.access_token,
    tokenType: data.token_type,
  };
};
