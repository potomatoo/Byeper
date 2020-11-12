<template>
  <div class="editor">
    <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
      <div class="menubar">
        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.bold() }"
          @click="commands.bold"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/bold.svg"
            alt="bold"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.italic() }"
          @click="commands.italic"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/italic.svg"
            alt="italic"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.strike() }"
          @click="commands.strike"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/strike.svg"
            alt="strike"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.underline() }"
          @click="commands.underline"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/underline.svg"
            alt="underline"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.code() }"
          @click="commands.code"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/code.svg"
            alt="code"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.paragraph() }"
          @click="commands.paragraph"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/paragraph.svg"
            alt="paragraph"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 1 }) }"
          @click="commands.heading({ level: 1 })"
        >
          H1
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 2 }) }"
          @click="commands.heading({ level: 2 })"
        >
          H2
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.heading({ level: 3 }) }"
          @click="commands.heading({ level: 3 })"
        >
          H3
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.bullet_list() }"
          @click="commands.bullet_list"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/ul.svg"
            alt="ul"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.ordered_list() }"
          @click="commands.ordered_list"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/ol.svg"
            alt="ol"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.blockquote() }"
          @click="commands.blockquote"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/quote.svg"
            alt="quote"
          />
        </button>

        <button
          class="menubar__button"
          :class="{ 'is-active': isActive.code_block() }"
          @click="commands.code_block"
        >
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/code.svg"
            alt="code"
          />
        </button>

        <button class="menubar__button" @click="commands.horizontal_rule">
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/hr.svg"
            alt="hr"
          />
        </button>

        <button class="menubar__button" @click="commands.undo">
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/undo.svg"
            alt="undo"
          />
        </button>

        <button class="menubar__button" @click="commands.redo">
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/redo.svg"
            alt="redo"
          />
        </button>

        <!-- <button class="menubar__button" @click="commands.image">
          <img
            class="editor-icon"
            src="@/assets/tiptap/icons/image.svg"
            alt="image"
          />
        </button> -->
      </div>
    </editor-menu-bar>

    <editor-content class="editor__content" :editor="editor" />
    <div id="focus-position"></div>
    <image-capture-chip
      :player="player"
      :videoId="videoId"
      @addCapture="addCapture"
    />
  </div>
</template>

<script>
import { Component, Vue, Prop } from "vue-property-decorator";
import { Editor, EditorContent, EditorMenuBar } from "tiptap";
import {
  Blockquote,
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  OrderedList,
  BulletList,
  ListItem,
  TodoItem,
  TodoList,
  Bold,
  Code,
  Italic,
  Strike,
  Underline,
  History,
  Image
} from "tiptap-extensions";
import ImageCaptureChip from "@/components/lecture/ImageCaptureChip.vue";

@Component({
  components: {
    EditorContent,
    EditorMenuBar,
    ImageCaptureChip
  }
})
export default class TextEditor extends Vue {
  @Prop(Object) player;
  @Prop(String) videoId;
  editor = new Editor({
    extensions: [
      new Blockquote(),
      new BulletList(),
      new CodeBlock(),
      new HardBreak(),
      new Heading({ levels: [1, 2, 3] }),
      new HorizontalRule(),
      new ListItem(),
      new OrderedList(),
      new TodoItem(),
      new TodoList(),
      new Bold(),
      new Code(),
      new Italic(),
      new Strike(),
      new Underline(),
      new History(),
      new Image()
    ],
    content: `
          <h2>
            Hi there,
          </h2>
          <p>
            this is a very <em>basic</em> example of tiptap.
          </p>
          <pre><code>body { display: none; }</code></pre>
          <ul>
            <li>
              A regular list
            </li>
            <li>
              With regular items
            </li>
          </ul>
          <blockquote>
            It's amazing 👏
            <br />
            – mom
          </blockquote>
        `,
    autoFocus: true
  });
  beforeDestroy() {
    this.editor.destroy();
  }

  async addCapture(imgUrl) {
    this.editor.setContent(
      `${this.editor.getHTML()}<p><img draggable="true" contenteditable="false" src="${imgUrl}"><br></p>`
    );
    this.editor.focus();
    const focused = await document.querySelector("#focus-position");
    this.$vuetify.goTo(focused.offsetTop);
  }
}
</script>

<style lang="scss" scoped>
.editor-icon {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0 0.3rem;
  top: -0.05rem;
  fill: currentColor;
  // &.has-align-fix {
  // 	top: -.1rem;
  // }
  &__svg {
    display: inline-block;
    vertical-align: top;
    width: 100%;
    height: 100%;
  }
  &:first-child {
    margin-left: 0;
  }
  &:last-child {
    margin-right: 0;
  }
}
// svg sprite
body > svg,
.editor-icon use > svg,
symbol {
  path,
  rect,
  circle,
  g {
    fill: currentColor;
    stroke: none;
  }
  *[d="M0 0h24v24H0z"] {
    display: none;
  }
}
.menubar {
  margin: 20px 0px;
}
.editor__content {
  color: black;
}
</style>
