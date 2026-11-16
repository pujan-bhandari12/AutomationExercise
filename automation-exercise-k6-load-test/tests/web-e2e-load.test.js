import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';
import { BASE_URL, DEFAULT_THRESHOLDS, LOAD_STAGES } from '../config/thresholds.js';

export const options = {
  stages: LOAD_STAGES,
  thresholds: DEFAULT_THRESHOLDS,
};

export default function () {
  group('Web_Storefront_Browsing_Journey', () => {
    const homeRes = http.get(`${BASE_URL}/`, { tags: { type: 'web', page: 'home' } });
    check(homeRes, { 'Home 200': (r) => r.status === 200 });
    sleep(1);

    const productsRes = http.get(`${BASE_URL}/products`, { tags: { type: 'web', page: 'products' } });
    check(productsRes, { 'Products 200': (r) => r.status === 200 });
    sleep(1);

    const detailRes = http.get(`${BASE_URL}/product_details/1`, {
      tags: { type: 'web', page: 'product_detail' },
    });
    check(detailRes, {
      'Product Detail 200': (r) => r.status === 200,
      'Contains Blue Top': (r) => r.body.includes('Blue Top'),
    });
    sleep(1);

    const catRes = http.get(`${BASE_URL}/category_products/1`, {
      tags: { type: 'web', page: 'category' },
    });
    check(catRes, { 'Category 200': (r) => r.status === 200 });
    sleep(1);

    const cartRes = http.get(`${BASE_URL}/view_cart`, { tags: { type: 'web', page: 'cart' } });
    check(cartRes, { 'Cart 200': (r) => r.status === 200 });

    const loginRes = http.get(`${BASE_URL}/login`, { tags: { type: 'web', page: 'login' } });
    check(loginRes, { 'Login 200': (r) => r.status === 200 });
  });

  sleep(1);
}

export function handleSummary(data) {
  return {
    'reports/web-load-report.html': htmlReport(data),
    'reports/web-load-summary.json': JSON.stringify(data, null, 2),
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
  };
}
