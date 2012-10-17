#!/usr/bin/env python
import sys
import re

greek_alphabets = ['alpha', 'beta', 'gamma', 'delta', 'epsilon', 'zeta', 'eta', 'theta', 
                   'iota', 'kappa', 'lambda', 'mu', 'nu', 'xi', 'omicron', 'pi', 
                   'rho', 'sigma', 'tau', 'upsilon', 'phi', 'chi', 'psi', 'omega']

def is_digit(chr_in):
    return (ord(chr_in) >= ord('0') and ord(chr_in) <= ord('9'))

def is_number(target_str):
    return (re.search("^[0-9]+$", target_str) is not None)

def split_numbers(target_str):
    if (len(target_str) == 0):
        return None
    
    i = 0
    clusters = list()
    curr_cluster = ""
    curr_cluster_number = is_digit(target_str[0])
    while (i < len(target_str)):
        if (is_digit(target_str[i])):
            if (curr_cluster_number):
                curr_cluster += target_str[i]
            else:
                clusters.append(curr_cluster)
                curr_cluster = target_str[i]
                curr_cluster_number = True
        elif (not is_digit(target_str[i])):
            if (not curr_cluster_number):
                curr_cluster += target_str[i]
            else:
                clusters.append(curr_cluster)
                curr_cluster = target_str[i]
                curr_cluster_number = False
        i += 1  
    clusters.append(curr_cluster)
    return clusters              

def is_roman_numerals(target_str):
    return (re.search("^[IVX]+$", target_str) is not None)

def get_greek_alphabet_ptn():
    greek_alphabet_ptn = "\\b("
    for w in greek_alphabets:
        greek_alphabet_ptn += w + "|"
    
    if (greek_alphabet_ptn[-1] == "|"):
        greek_alphabet_ptn = greek_alphabet_ptn[:-1]
    greek_alphabet_ptn += ")\\b"
    
    return greek_alphabet_ptn

def is_greek_alphabet(target_str):
    return (target_str.lower().strip() in greek_alphabets)

def is_single_letter(target_str):
    return (re.search("^[A-Z]$", target_str) is not None)

def extract_regex():
    regex_list = list()
    
    for line in sys.stdin:
        line = line.replace("\n", "")
        tokens = line.split("|")
        if (len(tokens) != 3):
            sys.stderr.write("Unable to process %s ... \n"%(line))
            return None
        
        gene = tokens[-1]
        connection = "[ ]"
        # gene_tokens = gene.split("[ -,\./()]")
        gene_tokens = re.split(connection, gene)
        gene_tokens_num = list()
        # print gene_tokens
        for t in gene_tokens:
            if (t is not None and len(t) > 0):
                gene_tokens_num.extend(split_numbers(t))
                gene_tokens_num.append(" ")
        
        if (gene_tokens_num[-1] == " "):
            del gene_tokens_num[-1]
            
        # print gene_tokens_num
        gene_regex = ""
        
        
        for t in gene_tokens_num:
            t = re.escape(t)
            if (is_number(t)):
                gene_regex += "[0-9]+"
            elif (is_roman_numerals(t)):
                gene_regex += "[IVX]+"
            elif (is_greek_alphabet(t)):
                gene_regex += get_greek_alphabet_ptn()
            elif (is_single_letter(t)):
                gene_regex += "[A-Z]"
            elif (t == "\ "):
                gene_regex += connection
            else:
                gene_regex += t
                
            # gene_regex += connection
        if (gene_regex not in regex_list):
            if (not gene_regex.startswith("\\b")):
                gene_regex = "\\b" + gene_regex
            if (not gene_regex.endswith("\\b")):
                gene_regex = gene_regex + "\\b"
            if (gene_regex != "\\b[A-Z]\\b" and gene_regex != "\\b" + get_greek_alphabet_ptn() +"\\b"):
                regex_list.append(gene_regex)
   
    regex_list = list(set(regex_list))
    return regex_list

def regsubs(str_in):
    pass
        

def main():
    # print split_numbers("abc")
    regex_list = extract_regex()
    for regex in regex_list:
        print regex
    

if __name__ == "__main__":
    sys.exit(main())
    