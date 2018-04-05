#!/usr/bin/python

import requests
import unittest
import json

host = "http://localhost:5676"


class MyTest(unittest.TestCase):
    def test_health_check(self):
        r = requests.get("{}/public/health".format(host))
        self.assertEqual(r.status_code, 200)

    # @unittest.skip("testing skipping")
    def test_create_reminder(self):
        email = "nobody@example.com"
        password = "secret"
        delete_user_by_email(email)
        payload = {
            "first_name": "Chuck",
            "last_name": "Norris",
            "email": email,
            "password": password,
            "agreed_terms": "true",
            "user_type": {
                "type": "user"
            },
            "is_admin": "false"
        }
        create_user(payload)
        token = get_token_by_email(email)
        verify_email_token(token)
        login_payload = {
            "email": email,
            "password": password
        }
        jwt_token = login_user(login_payload)
        headers = {'Authorization': jwt_token}
        # Hello world reminder
        print 'Adding Hello world reminder'
        reminder_payload = {
            "event_name": "Hello World",
            "schedule": "* * * * *",
            "recipients": [
                "leonj1+helloworld@gmail.com"
            ]
        }
        r = requests.post("{}/private/reminders".format(host), json=reminder_payload, headers=headers)
        self.assertEquals(r.content, "saved")
        self.assertEqual(r.status_code, 200)
        # Happy Birthday reminder
        print 'Adding Happy Birthday reminder'
        reminder_payload = {
            "event_name": "Happy Birthday",
            "schedule": "* * 27 6 *",
            "recipients": [
                "leonj1+happybirthday@gmail.com"
            ]
        }
        r = requests.post("{}/private/reminders".format(host), json=reminder_payload, headers=headers)
        self.assertEquals(r.content, "saved")
        self.assertEqual(r.status_code, 200)


def delete_user_by_email(email):
    r = requests.delete("{}/public/users/{}".format(host, email))


def create_user(payload):
    print 'Creating user'
    r = requests.post("{}/public/users/register".format(host), json=payload)
    if r.status_code != 201:
        raise Exception('User not created. Status: {}'.format(r.status_code))


def get_token_by_email(email):
    print 'Getting token from email'
    r = requests.get("{}/public/admin/token/{}".format(host, email))
    if r.status_code != 200:
        raise Exception('Token not fetched. Status: {}'.format(r.status_code))
    print 'Token: {}'.format(r.content)
    return r.content


def verify_email_token(token):
    print 'Verifying email token'
    r = requests.get("{}/public/verify/email/{}".format(host, token))
    if r.status_code != 200:
        raise Exception('Token not verified. Status: {}'.format(r.status_code))
    print 'Response: {}'.format(r.content)
    return r.content


def login_user(payload):
    print 'Log in user'
    payload = {
        "email": payload["email"],
        "password": payload["password"]
    }
    r = requests.post("{}/public/users/login".format(host), json=payload)
    d = json.loads(r.content)
    print 'JWT Token: {}'.format(d["token"])
    return d["token"]


if __name__ == '__main__':
    unittest.main()

