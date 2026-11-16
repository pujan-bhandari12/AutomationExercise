import http from 'k6/http';
import { group, sleep } from 'k6';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';
import { BASE_URL, DEFAULT_THRESHOLDS, LOAD_STAGES } from '../config/thresholds.js';
import { generateUniqueUser, checkApiStatus } from '../utils/helpers.js';

export const options = {
  stages: LOAD_STAGES,
  thresholds: DEFAULT_THRESHOLDS,
};

export default function () {
  const user = generateUniqueUser(__VU, __ITER);

  group('01_Catalog_APIs', () => {
    const productsRes = http.get(`${BASE_URL}/api/productsList`, {
      tags: { type: 'api', endpoint: 'productsList' },
    });
    checkApiStatus(productsRes, 200, 'Get All Products');

    const brandsRes = http.get(`${BASE_URL}/api/brandsList`, {
      tags: { type: 'api', endpoint: 'brandsList' },
    });
    checkApiStatus(brandsRes, 200, 'Get All Brands');

    const searchRes = http.post(
      `${BASE_URL}/api/searchProduct`,
      { search_product: 'tshirt' },
      { tags: { type: 'api', endpoint: 'searchProduct' } }
    );
    checkApiStatus(searchRes, 200, 'Search Product (tshirt)');
  });

  sleep(1);

  group('02_E2E_User_Account_Lifecycle_API', () => {
    const createRes = http.post(`${BASE_URL}/api/createAccount`, user, {
      tags: { type: 'api', endpoint: 'createAccount' },
    });
    checkApiStatus(createRes, 201, 'Create User Account');

    const loginRes = http.post(
      `${BASE_URL}/api/verifyLogin`,
      { email: user.email, password: user.password },
      { tags: { type: 'api', endpoint: 'verifyLogin' } }
    );
    checkApiStatus(loginRes, 200, 'Verify Valid Login');

    const invalidLoginRes = http.post(
      `${BASE_URL}/api/verifyLogin`,
      { email: user.email, password: 'WrongPassword!' },
      { tags: { type: 'api', endpoint: 'verifyLoginInvalid' } }
    );
    checkApiStatus(invalidLoginRes, 404, 'Verify Invalid Login Rejected');

    const userDetailRes = http.get(
      `${BASE_URL}/api/getUserDetailByEmail?email=${encodeURIComponent(user.email)}`,
      { tags: { type: 'api', endpoint: 'getUserDetail' } }
    );
    checkApiStatus(userDetailRes, 200, 'Get User Detail By Email');

    const deleteRes = http.request(
      'DELETE',
      `${BASE_URL}/api/deleteAccount`,
      { email: user.email, password: user.password },
      { tags: { type: 'api', endpoint: 'deleteAccount' } }
    );
    checkApiStatus(deleteRes, 200, 'Delete User Account');
  });

  sleep(1);
}

export function handleSummary(data) {
  return {
    'reports/api-load-report.html': htmlReport(data),
    'reports/api-load-summary.json': JSON.stringify(data, null, 2),
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
  };
}
