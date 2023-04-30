import { PactV3, MatchersV3 } from '@pact-foundation/pact';
import path from 'path';
import { Application, Attachment } from '../generated';
import { ApplicationApi } from './ApplicationApi';

const provider = new PactV3({
  dir: path.resolve(process.cwd(), 'pacts'),
  consumer: 'MyConsumer',
  provider: 'MyProvider',
});

const applicationExample: Application = {
    id: 0,
    title: 'title',
    created: new Date(),
    attachments: <Attachment[]>[
      {created: new Date(), id: 1, title: 'title'}
    ]
};

describe('GET /applications', () => {
  it('returns an HTTP 200 and a defeault list of applications and their attachments', () => {
    provider
      .given('I have a list of applications')
      .uponReceiving('a request for all applications with the builder pattern')
      .withRequest({
        method: 'GET',
        path: '/applications',
        headers: { Accept: 'application/json' },
      })
      .willRespondWith({
        status: 200,
        headers: { 'Content-Type': 'application/json' },
        body: MatchersV3.eachLike(applicationExample, 1),
      });

    return provider.executeTest(async (mockserver) => {
      const client = new ApplicationApi(mockserver.url);
      const response: Application[] = await client.getApplications();
      expect(response).toEqual([applicationExample])
    });
  });

  it('returns an HTTP 204 and empty list', () => {
    provider
      .given('I have no applications')
      .uponReceiving('a request for all applications with the builder pattern')
      .withRequest({
        method: 'GET',
        path: '/applications',
        headers: { Accept: 'application/json' },
      })
      .willRespondWith({
        status: 204,
        headers: { 'Content-Type': 'application/json' },
      });
      
      return provider.executeTest(async (mockserver) => {
        const client = new ApplicationApi(mockserver.url);
        const response: Application[] = await client.getApplications();
        expect(response).toHaveLength(0);
      });
  });

});