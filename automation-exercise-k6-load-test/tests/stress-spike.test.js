import http from 'k6/http';
import { sleep } from 'k6';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';
import { BASE_URL, STRESS_SPIKE_STAGES } from '../config/thresholds.js';
import { checkApiStatus } from '../utils/helpers.js';

export const options = {
  stages: STRESS_SPIKE_STAGES,
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<3000'],
  },
};

const KEYWORDS = ['top', 'tshirt', 'dress', 'jeans', 'saree'];

export default function () {
  const keyword = KEYWORDS[Math.floor(Math.random() * KEYWORDS.length)];

  const productsRes = http.get(`${BASE_URL}/api/productsList`, {
    tags: { type: 'api', scenario: 'stress' },
  });
  checkApiStatus(productsRes, 200, 'Stress GET Products');

  const searchRes = http.post(
    `${BASE_URL}/api/searchProduct`,
    { search_product: keyword },
    { tags: { type: 'api', scenario: 'stress' } }
  );
  checkApiStatus(searchRes, 200, `Stress Search (${keyword})`);

  sleep(0.5);
}

export function handleSummary(data) {
  return {
    'reports/stress-spike-report.html': htmlReport(data),
    'reports/stress-spike-summary.json': JSON.stringify(data, null, 2),
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
  };
}
