import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URL } from '../config/thresholds.js';
import { checkApiStatus } from '../utils/helpers.js';

export const options = {
  vus: 2,
  duration: '15s',
  thresholds: {
    http_req_failed: ['rate==0'],
    http_req_duration: ['p(95)<2000'],
  },
};

export default function () {
  const homeRes = http.get(`${BASE_URL}/`, { tags: { type: 'web' } });
  check(homeRes, {
    'Home page status 200': (r) => r.status === 200,
    'Home page contains Automation Exercise': (r) => r.body.includes('Automation Exercise'),
  });

  const productsRes = http.get(`${BASE_URL}/api/productsList`, { tags: { type: 'api' } });
  checkApiStatus(productsRes, 200, 'GET /api/productsList');

  const brandsRes = http.get(`${BASE_URL}/api/brandsList`, { tags: { type: 'api' } });
  checkApiStatus(brandsRes, 200, 'GET /api/brandsList');

  sleep(1);
}
