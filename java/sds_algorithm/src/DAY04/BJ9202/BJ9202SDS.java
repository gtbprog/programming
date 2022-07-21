package sds_algorithm.src.DAY04.BJ9202;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ9202SDS {
    static int[] my = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] mx = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int[] score = {0, 0, 0, 1, 1, 2, 3, 5, 11};

    static int W, N;
    static char[][] map;
    static boolean[][] visited;
    static String answer;
    static int sum;
    static int count;
    // StringBuilder ~ not thread-safe
    // StringBuffer ~ thread-safe ... 무언가를 체크를 더 하는 로직이 들어감
    static StringBuilder sb = new StringBuilder();
    static TrieNode root = new TrieNode();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        W = Integer.parseInt(br.readLine());
        for (int i = 0; i < W; i++) {
            insertTrieNode(br.readLine());
        }

        br.readLine();  // empty

        N = Integer.parseInt(br.readLine());
        StringBuilder resultSb = new StringBuilder();
        for (int n = 0; n < N; n++) {
            map = new char[4][4];
            visited = new boolean[4][4];
            answer = "";
            sum = 0;
            count = 0;
            sb = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                String in = br.readLine();
                for (int k = 0; k < 4; k++) {
                    map[i][k] = in.charAt(k);
                }
            }
            br.readLine();
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    // 출발 가능 조건 -> root가 해당 child를 가지면
                    if (root.hasChild(map[y][x])) {
                        search(y, x, root.getChild(map[y][x]));
                    }
                }
            }
            // 결과 출력
            root.clearHit();
        }
        System.out.println(resultSb.toString());
    }

    static void search(int y, int x, TrieNode node) {
        // 1. 체크인
        visited[y][x] = true;
        sb.append(map[y][x]);
        // 2. 목적지에 도달하였는가? -> isEnd, isHit
        if (node.isWord == true && node.isHit == false) {
            node.isHit = true;
            // 답 작업 -> 길이, 단어
            String foundWord = sb.toString();
            int length = foundWord.length();
        }
        // 3. 연결된 곳을 순회 -> 8방
        for (int i = 0; i < 8; i++) {
            int ty = y + my[i];
            int tx = x + mx[i];
            // 4. 가능한가? - map 경계, 방문하지 않았는지, node가 해당 지식을 가지고 있는지
            if (-1 < ty && ty < 4 && -1 < tx && tx < 4) {
                if (visited[ty][tx] == false && node.hasChild(map[ty][tx])) {
                    // 5. 간다
                    search(ty, tx, node.getChild(map[ty][tx]));
                }
            }
        }
        // 6. 체크아웃
        visited[y][x] = false;
        sb.deleteCharAt(sb.length() - 1);
    }

    static void insertTrieNode(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char a = word.charAt(i);
            int index = a - 'A';
            if (current.child[index] == null) {
                current.child[index] = new TrieNode();
            }
            current = current.child[index];
        }
        current.isWord = true;
    }
}

class TrieNode {
    boolean isWord = false;
    boolean isHit = false;
    TrieNode[] child = new TrieNode[26];

    void clearHit() {
        isHit = false;
        for (int i = 0; i < child.length; i++) {
            if (child[i] != null) { child[i].clearHit(); }
        }
    }

    boolean hasChild(char c) {
        return child[c - 'A'] != null;
    }

    TrieNode getChild(char c) {
        return child[c - 'A'];
    }
}