import csv
import datetime
import json
import re
import requests
import sys

# f = open('issues.json', encoding='utf-8')

GITHUB_API_URL = 'https://api.github.com'
GITHUB_REPO = 'reponame'
GIT_AUTH = requests.auth.HTTPBasicAuth('account', 'github_pat_secrect')

def load_json():
    f = open('issues.json', encoding='utf-8')
    data = json.load(f)
    f.close()
    return data

def git_issue_json():
    response = requests.get(GITHUB_API_URL + '/repos/' + GITHUB_REPO + '/issues?milestone=1&pulls=false&state=closed&sort=created&direction=asc&per_page=100', auth=GIT_AUTH)
    print(response)
    data = response.json()
    #decoded_response = response.read().decode("UTF-8")
    #data = json.load(decoded_response)
    #response.close()
    return data


def git_comms_json(number):
    response_comms = requests.get(GITHUB_API_URL + '/repos/' + GITHUB_REPO + '/issues/' + str(number) + '/comments', auth=GIT_AUTH)
    comms_all = response_comms.json()
    response_comms.close()
    return comms_all

def issue_labels_str(labels):
    ret_labels = []    
    for label in labels:
        ret_labels.append(label['name'])
    return ','.join(ret_labels)

def issue_work_time(number):
    work_time = None
    for comm in git_comms_json(number):
        m = re.search('^(\\s*)([\\d\\.]+)\\s*h\\s*$', comm['body'])
        if (m != None):
            work_time = m.group(2)
    return None if work_time == None else float(work_time)
    
now_str = datetime.datetime.now().strftime('%m-%d-%Y_%H-%M-%S')

fout = open('issue_list_export_' + now_str + '.csv', 'w')


issues = git_issue_json()
# issues = load_json()

csv_writer = csv.writer(fout, delimiter='\t', quoting=csv.QUOTE_NONNUMERIC, lineterminator='\n', doublequote=True)

headers = ['Number', 'Milestone', 'Title', 'Labels', 'Work_Time', 'Issue_URL']    
print(headers)
csv_writer.writerow(headers)

total_work_time = 0.00

for issue in issues:
    number = issue['number']
    work_time = issue_work_time(number)
    if work_time != None: 
        total_work_time += work_time
    row = [number, issue['milestone']['title'], issue['title'], issue_labels_str(issue['labels']), work_time, issue['url'] ]    
    print(row)
    csv_writer.writerow(row)


sum_row = [None, None, 'TOTAL', None, total_work_time, None ]
print(sum_row)
csv_writer.writerow(sum_row)


fout.close()

print("DONE.")



